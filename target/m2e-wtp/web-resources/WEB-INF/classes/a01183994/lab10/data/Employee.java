package a01183994.lab10.data;

import java.time.LocalDate;

import a01183994.lab10.database.util.ApplicationException;

public class Employee {
	private static final LocalDate DEFAULT_DATE_OF_BIRTH;

	private String id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

	static {
		DEFAULT_DATE_OF_BIRTH = null;
	}

	public Employee(final String id, final String firstName, final String lastName, final LocalDate dateOfBirth)
			throws ApplicationException {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public Employee(final String id, final String firstName, final String lastName) throws ApplicationException {
		this(id, firstName, lastName, DEFAULT_DATE_OF_BIRTH);
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
}
