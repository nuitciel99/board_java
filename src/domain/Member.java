package domain;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Member {
	private final String id;
	private final String pwd;
	private final String name;
	private final Date regDate;
	private final String email;
	private final boolean admin;
}
