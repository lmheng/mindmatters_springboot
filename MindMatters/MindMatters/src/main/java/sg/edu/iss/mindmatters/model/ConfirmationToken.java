package sg.edu.iss.mindmatters.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.mindmatters.validation.PasswordValidation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tokenid;

    private String confirmationToken;

    private ZonedDateTime createdDate;

    @ManyToOne
    @Valid
    @NotNull(groups = {PasswordValidation.class})
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        createdDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Singapore"));
        confirmationToken = UUID.randomUUID().toString();
    }

}
