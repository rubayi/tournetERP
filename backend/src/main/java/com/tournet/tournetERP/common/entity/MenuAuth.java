package com.tournet.tournetERP.common.entity;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : MenuAuth
 * @since : 2024-03-12
 */
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "COM_MENU_AUTH")
public class MenuAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_AUTH_UUID", nullable = false)
    private long menuAuthUuid; //등급UUID

    @Column(name = "MENU_UUID", nullable = true)
    private long menuUuid; //읽기

    @Column(name = "MENU_READ", nullable = true)
    private String menuRead; //읽기

    @Column(name = "MENU_WRITE", nullable = true)
    private String menuWrite; //수정

    @Column(name = "MENU_UPDATE", nullable = true)
    private String menuUpdate; //수정

    @Column(name = "MENU_DELETE", nullable = true)
    private String menuDelete; //삭제

    @Column(name = "EMP_UUID", nullable = false)
    private long empUuid;

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = false)
    private Date modifiedDt;

    public MenuAuth () {
    }

    public MenuAuth (int menuAuthUuid, long menuUuid,
                     String menuRead,  String menuWrite, String menuUpdate,
                     String menuDelete   ) {

        this.menuAuthUuid=menuAuthUuid;
        this.menuUuid=menuUuid;
        this.menuRead=menuRead;
        this.menuWrite=menuWrite;
        this.menuUpdate=menuUpdate;
        this.menuDelete=menuDelete;

    }
}


