package com.example.signaturevalidationserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "files")
public class VerifiedFiles {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long file_id;
    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] content;

    @Column(name = "fileName", nullable = false)
    private String fileName;
    @Column(name = "filePath", nullable = false)
    private String filePath;
    @Column(name = "uploadDate")
    private LocalDateTime uploadDate;
    @Column(name = "isValid")
    private boolean isValid;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false, referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VerifiedSignatures> signatures = new ArrayList<>();

    public VerifiedFiles() {

    }

    public VerifiedFiles(byte[] content, String fileName, String filePath, User user) {
        this.content = content;
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadDate = LocalDateTime.now();
        this.user = user;
    }

    public VerifiedFiles(String fileName, String filePath, LocalDateTime uploadDate, User user){
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadDate = uploadDate;
        this.user = user;
    }
    public VerifiedFiles(String fileName, String filePath, User user){
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadDate = LocalDateTime.now();
        this.user = user;
    }


}
