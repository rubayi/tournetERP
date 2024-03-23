package com.tournet.tournetERP.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "EMP_AUTH")
public class EmpMenuAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_AUTH_UUID", nullable = false)
    private long empAuthUuid;

    @NotBlank
    @Size(max = 20)
    @Column(name = "EMP_UUID", nullable = false)
    private long empUuid;

    @NotBlank
    @Size(max = 20)
    @Column(name = "MENU_AUTH_UUID", nullable = true)
    private long menuAuthUuid;

}