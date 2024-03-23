package com.tournet.tournetERP.auth.entity;

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

    @Column(name = "MENU_AUTH_NAME", nullable = true)
    private String menuAuthName; //권한명

    @Column(name = "MENU_AUTH_NAME_ENG", nullable = true)
    private String menuAuthNameEng; //권한명

    @Column(name = "AUTH_YN", nullable = true)
    private String authYn; //수정

    @Column(name = "GROUP_CODE", nullable = true)
    private String groupCode; //수정

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = false)
    private Date modifiedDt;

}


