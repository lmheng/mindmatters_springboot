package sg.edu.iss.mindmatters.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Registration.class, Expensive.class})
public interface ValidationSequence {
}