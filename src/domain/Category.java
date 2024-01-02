package domain;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class Category {
	private final int cno;
	private final String cname;
	private final String cdesc;
	private final Date regDate;
}
