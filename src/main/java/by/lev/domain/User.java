package by.lev.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Date;
import java.sql.Timestamp;

@Setter
@Getter
@EqualsAndHashCode
@Builder
@ToString //(exclude = {"userName"})
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private Date birth;

    private String phoneNumber;

    private String email;

    private String secretWord;

    private String secretWordHint;

    private Timestamp registrationDate;

    private Timestamp modificationDate;

    private Boolean isDeleted;

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
//    }
}

