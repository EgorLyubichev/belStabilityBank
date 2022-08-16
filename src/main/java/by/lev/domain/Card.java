package by.lev.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Builder
@ToString
public class Card {

    private Long id;

    private Long cardNumber;

    private Timestamp registrationDate;

    private Timestamp modificationDate;

    private Date validPeriod;

    private Boolean isActive;

    private Boolean isBlocked;

}
