package admin;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class notice_dao {
	int nidx;
	String ntop, nsubject, nwrite, ntext, nnum, ndate;
	String nattach;
}
