package mock;

import model.Notification;
import domein.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;


public class Model {
	public static final String ipsum =
			"""
					Lorem Ipsum is simply dummy text of the printing and typesetting industry.
					Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
					It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
					It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
					""";
//	public static final ObservableList<User> people;
	public static final ObservableList<Notification> notifications;

//    static {
//        people = FXCollections.observableArrayList(
//                new User("CEO", "Qwertic", "Plaza", "qwertic@qwict.com", "admin"),
//                new User("ALEXANDER", "Alexander", "Schatteman", "alexander@qwict.com", "employee"),
//                new User("JORIS", "Joris", "Van Duyse", "joris@qwict.com", "warehouseman"),
//                new User("LAURENS", "Laurens", "De Maeyer", "laurens@qwict.com", "employee"),
//                new User("LEVI", "Levi", "Van Achter", "levi@qwict.com", "employee"),
//                new User("DAAN", "Daan", "Van Landuyt", "daan@qwict.com", "employee")
//        );
//    }
    /*public static final ObservableList<Order> orders;

    static {
        orders = FXCollections.observableArrayList(
                new Order("Qwict", "Laurens", "REF1", LocalDate.now(), "JansFranswillem", 5, "No track and trace code yet", "1"),
                new Order("Qwict", "Qwertic", "REF2", LocalDate.now(),"SintPauwels", 5, "46987896598746", "1")
        );
    }*/

    static {
        notifications = FXCollections.observableArrayList(
                new Notification(
                        "0ebc10c4-a784-4b5a-8efd-b549089f02d6",
                        "12165d36-cb10-4e12-8d6b-10cdf8f5f9f1",
                        "95e3745e-7a8d-4947-9ff1-59f76544564b",
                        1,
                        "Sat Mar 04 2023 12:09:30 GMT+0100 (Central European Standard Time)",
                        "Order information",
                        "Order by Joris, this notification should be visible for all employees and admins in a company",
                        "company",
                        null,
                        null
                ),
                new Notification(
                        "373d4b73-80c2-4ff8-8b60-e4894d90ddd5",
                        null,
                        "2b93f1c4-38bd-490d-a0ca-f7b81b9de171",
                        1,
                        "Sat Mar 04 2023 12:09:30 GMT+0100 (Central European Standard Time)",
                        "Random information",
                        "This notification is a random announcement for all employees and admins in a company",
                        "company",
                        "qwertic@qwict.com",
                        null
                ),
                new Notification(
                        "02374203-396f-4391-96b2-6b5ec2238943",
                        "12165d36-cb10-4e12-8d6b-10cdf8f5f9f1",
                        "2b93f1c4-38bd-490d-a0ca-f7b81b9de171",
                        null,
                        "Sat Mar 04 2023 12:09:30 GMT+0100 (Central European Standard Time)",
                        "Order notification",
                        "Private Order notification that should be archived",
                        "private",
                        null,
                        null
                ),
                new Notification(
                        "3ce7efd7-0bb3-4719-ad62-938f4b2d3342",
                        null,
                        "95e3745e-7a8d-4947-9ff1-59f76544564b",
                        1,
                        "Sat Mar 04 2023 12:09:30 GMT+0100 (Central European Standard Time)",
                        "Employee account created",
                        "This employee account was created by a seed. This notification is only for admins",
                        "admin",
                        "qwertic@qwict.com",
                        null
                )
        );
    }
}
