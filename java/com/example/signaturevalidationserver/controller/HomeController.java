package com.example.signaturevalidationserver.controller;
import com.example.signaturevalidationserver.model.*;
import com.example.signaturevalidationserver.model.exceptions.NoSignatureException;
import com.example.signaturevalidationserver.service.FileService;
import com.example.signaturevalidationserver.service.SignatureValidationService;
import com.example.signaturevalidationserver.service.UserService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String home() { return "index"; }
    @Autowired private SignatureValidationService validationService;
    @Autowired private FileService fileService;
    @Autowired private UserService userService;

    @PostMapping("/validate")
    public String validateSignatures(@RequestParam("file") MultipartFile file, Model model) {
        model.addAttribute("fileName", file.getOriginalFilename());
        try {
            // Save the uploaded file to a temporary location
            File tempFile = File.createTempFile("uploaded", file.getOriginalFilename());
            file.transferTo(tempFile);

            // Call the service to validate the digital signatures

            Pair<VerifiedFiles, List<VerifiedSignatures>> validation =
                    validationService.validateDigitalSignatures(tempFile);


            boolean isValid = validation.getLeft().isValid();
            List<VerifiedSignatures> signatures = validation.getRight();

            // Add result to the model to display in the view
            model.addAttribute("isValid", isValid);
            model.addAttribute("signatureList", signatures);

            // Clean up the temporary file
            tempFile.deleteOnExit();

        } catch(NoSignatureException e){
            e.printStackTrace();
            return "no-signatures";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error uploading file");
            return "error";
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Idk!");
            return "error";
        }

        return "result";  // View to display the result
    }
    @GetMapping("/validated-files")
    public String showValidatedFiles(Model model){
        model.addAttribute("files", validationService.getAllVerifiedFiles());
        return "validated-files";
    }
    @GetMapping("/signatures")
    public String getResult(@RequestParam Long fileId, Model model) {
        List<VerifiedFiles> verifiedFiles = validationService.getAllVerifiedFiles();
        boolean access = false;
        boolean admin = userService.getCurrentUser().getRole().getAuthority().equals("ROLE_ADMIN");

        for(VerifiedFiles file : verifiedFiles){
            if(admin){
                access = true;
                break;
            }
            if(fileId == file.getFile_id()){
                access = true;
                break;
            }
        }
        if(!access) {
            model.addAttribute("error", "No access to this files signatures");
            return "error";
        }
        VerifiedFiles verifiedFile = fileService.getFileByFileId(fileId);
        List<VerifiedSignatures> signatures = validationService.getVerifiedSignaturesByFile(verifiedFile);

        model.addAttribute("fileName", verifiedFile.getFileName());
        model.addAttribute("isValid", verifiedFile.isValid());
        model.addAttribute("signatureList", signatures);
        return "result";
    }
    @GetMapping("/validated-signatures")
    public String getSignatures(Model model){
        model.addAttribute("signatureList", validationService.getAllVerifiedSignatures());
        return "signatures";
    }
}
