package com.tournet.tournetERP.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "EMP_MENU")
public class UserMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_MENU_UUID", nullable = false)
    private int empMenuUuid;

    @Column(name = "ROLE_UUID", nullable = true)
    private int roleUuid;

    @Column(name = "MENU_UUID", nullable = true)
    private int menuUuid;

    public UserMenu() {
    }

    public UserMenu(int roleUuid, int menuUuid) {

        this.roleUuid = roleUuid;
        this.menuUuid = menuUuid;

    }

}
