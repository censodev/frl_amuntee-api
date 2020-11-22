package com.printway.httpfile.models;

import com.printway.httpfile.utils.FileStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "files", schema = "printway_files_storage")
@Data
public class File {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;

    @Column(name = "name", length = 1000)
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "size")
    private long size;

    @Column(name = "status")
    private FileStatus status;
}
