package net.ralphpina.kamcordwatch.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility= NONE,
        getterVisibility = NONE,
        isGetterVisibility = NONE,
        setterVisibility = NONE,
        creatorVisibility = NONE)
public class TransitObject {
}
