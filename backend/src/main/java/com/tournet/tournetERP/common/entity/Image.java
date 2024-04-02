package com.tournet.tournetERP.common.entity;

/**
 * 이미지 관리
 *
 * @author : rubayi
 * @fileName : Image
 * @since : 2024-04-01
 */
import java.util.Date;

import com.tournet.tournetERP.auth.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_IMAGE")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_UUID", nullable = false)
    private long imgUuid;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_LOCATION")
    private String fileLoc;

    @Column(name = "IMG_ORD", nullable = false)
    private String imgOrd;

    @Column(name = "GROUP_IDX", nullable = false)
    private String grpIdx;

    @Column(name = "MAIN_YN", nullable = false)
    private String mainYn;

    @CreatedDate
    @Column(name = "CREATED_DT")
    private Date createdDt;

    @OneToOne
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "EMP_UUID",
            updatable = false)
    private User createUser;

    public Image() {
    }
}
