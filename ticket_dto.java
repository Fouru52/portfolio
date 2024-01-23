package admin;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ticket_dto {
	int tidx;
	String airplane, airname, aircode, start_point, end_point, seat_style, user_name, user_tel, user_person, money, tdate; 
}
