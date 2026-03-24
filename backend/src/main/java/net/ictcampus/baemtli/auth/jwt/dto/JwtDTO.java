package net.ictcampus.baemtli.auth.jwt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Wir übergeben dem User das Token als String und separat auch noch das Ablaufdatum.
 * Grundsätzlich ist das Datum auch im Token direkt vermerkt, aber einfachheitshalber
 * für das Frontend später, geben wir dies zusätzlich mit.
 */
@Getter
@Setter
@Accessors(chain = true)
public class JwtDTO {
    private String accessToken;
    private long expiresIn;
}
