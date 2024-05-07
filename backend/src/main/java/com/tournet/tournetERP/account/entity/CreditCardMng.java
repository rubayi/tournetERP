package com.tournet.tournetERP.account.entity;

/**
 * 프리페이드카드 관리!!
 *
 * @author : rubayi
 * @fileName : CreditCardMng
 * @since : 2024-04-08
 */
import com.tournet.tournetERP.auth.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 입력 용
@Table(name = "APP_CREDIT_CARD_MNG")
public class CreditCardMng {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CREDIT_CARD_UUID", nullable = false)
    private long cdCdUuid; //카드UUID

    @Column(name = "MNG_NAME_KOR", nullable = true)
    private String mngNameKor; //관리명KOR

    @Column(name = "MNG_NAME_ENG", nullable = true)
    private String mngNameEng; //관리명ENG

    @Column(name = "CARD_NUMBER", nullable = true)
    private String cardNumber; //카드번호

    @Column(name = "NAME_ON_CARD", nullable = true)
    private String nameOnCard; //명의자

    @Column(name = "EXP_MONTH", nullable = true)
    private String expMonth; //유효일달

    @Column(name = "EXP_YEAR", nullable = true)
    private String expYear; //유효일년

    @Column(name = "BEGIN_DT", nullable = true)
    private String beginDt; //시작일

    @Column(name = "END_DT", nullable = true)
    private String endDt; //종료일

    @Column(name = "USE_YN", nullable = true)
    private String useYn; //종료일

    @CreatedDate
    @Column(name = "CREATED_DT", updatable = false)
    private Date createdDt; //생성일

    @OneToOne
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "EMP_UUID",
            updatable = false)
    private User createUser;

    @OneToOne
    @JoinColumn(name = "MODIFIED_BY", referencedColumnName = "EMP_UUID")
    private User modifyUser;

    @LastModifiedDate
    @Column(name = "MODIFIED_DT", nullable = true)
    private Date modifiedDt; //최종수정일

    public CreditCardMng () {
    }

}
