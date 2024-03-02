package com.tournet.tournetERP.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "COM_MENU")
public class ComMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_UUID", nullable = false)
    private int menuUuid;

    @Column(name = "UPPER_MENU_UUID", nullable = true)
    private int upperMenuUuid;

    @Column(name = "MENU_LVL", nullable = true)
    private int menuLvl;

    @Column(name = "MENU_ICON", nullable = true)
    private String menuIcon;

    @Column(name = "MENU_KOR", nullable = true)
    private String menuKor;

    @Column(name = "MENU_ENG", nullable = false)
    private String menuEng;

    @Column(name = "MENU_URL", nullable = true)
    private String menuUrl;

    @Column(name = "MENU_ORD", nullable = true)
    private int menuOrd;

    @Column(name = "MENU_DESC", nullable = true)
    private String menuDesc;

    @Column(name = "USE_YN", nullable = true)
    private String useYn;

    @CreatedDate
    @Column(name = "CREATED_DT", updatable = true)
    private Date createdDt; //생성일

    @Column(name = "CREATED_BY", nullable = true)
    private int createdBy; //생성자

    @Column(name = "MODIFIED_BY", nullable = true)
    private Integer modifiedBy; //최종수정자

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = true)
    private Date modifiedDt; //최종수정일


    public ComMenu() {
    }

    public ComMenu(String menuKor, String menuEng, String menuIcon,
                   int upperMenuUuid, int menuLvl,
                   int menuOrd, String menuUrl,
                   String useYn, String menuDesc) {

        this.upperMenuUuid = upperMenuUuid;
        this.menuKor = menuKor;
        this.menuEng = menuEng;
        this.menuIcon = menuIcon;
        this.menuUrl = menuUrl;
        this.menuLvl = menuLvl;
        this.menuOrd = menuOrd;
        this.useYn = useYn;
        this.menuDesc = menuDesc;
    }

}
