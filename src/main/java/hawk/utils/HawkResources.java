package hawk.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

public class HawkResources {
	public static final List SUPPERUSERS = new ArrayList<Integer>(Arrays.asList(50, 51, 52));
	public static final List ADMINUSERS = new ArrayList<Integer>(Arrays.asList(31, 32));
	public static final List TRAINERS = new ArrayList<Integer>(Arrays.asList(21, 22));
	public static final List CLIENTS = new ArrayList<Integer>(Arrays.asList(11, 12));
	public static final List GUESTS = new ArrayList<Integer>(Arrays.asList(0, 1, 2));
	public static final List PD_ADMIN = new ArrayList<Integer>(Arrays.asList(900));
	

	public static final int CLIENT_USER_ROLE = 11;
	public static final String SUPPERUSER = "SUPPERUSER";
	public static final String ADMIN = "ADMIN";
	public static final String TRAINER = "TRAINER";
	public static final String CLIENT = "CLIENT";
	public static final String GUEST = "GUEST";
	public static final String PDADMIN = "PD_ADMIN";
	public static final String INVALID_USER = "INVALID";

	public static final String WEBSITE_ENQUIRY = "WEBSITE";
	public static final String SELF_ENTRY = "SELF";
	public static final String USERCONFIG_ENTRY_BY_SUPPER_USER = "Supper_User";
	public static final String HISTORY_AFFECTED_FIELD = "field";
	public static final String HISTORY_PREVIOUS_VALUE = "previous";
	public static final String HISTORY_UPDATED_VALUE = "updated";
	public static final String HISTORY_COMPONENT_CLIENT = "Client";
	public static final String HISTORY_COMPONENT_ENQUIRY = "Enquiry";
	public static final String HISTORY_COMPONENT_ACTIVITYS = "Activitys";
	public static final String HISTORY_COMPONENT_PACKAGE = "Package";
	public static final String HISTORY_COMPONENT_USERCONFIG = "UserConfig";
	public static final String HISTORY_COMPONENT_CALENDAREVENT = "CalendarEvent";
	public static final String HISTORY_COMPONENT_FEEDBACKTEMPLARE = "FeedbackTemplate";
	public static final String ENTRY_TYPE_RENEWAL = "Renewal";
	public static final String ENTRY_TYPE_NEW = "New Registration";
	public static final String PAYMENT_TRANSACTION_CREDIT = "Credit";
	public static final String PAYMENT_TRANSACTION_DEBIT = "Debit";
	public static final String PAYMENT_ENTRY_TYPE_PENDINGDUE = "PendingDue";

	public static final String HISTORY_COMPONENT_ANSWER = "Answer";

	public static final int ENQUIRY_TO_ENROLLED = 11;
	public static final int GET_ALL_PACKAGES_STATUS = 0;
	public static final int GET_ACTIVE_PACKAGES_STATUS = 1;

	public static final int RENEWAL_ALERT_DAYS = 7;
	public static final long PENDING_DUE_MIN_AMOUNT = 0;
	public static final long FEEDBACK_BETWEEN_DAYS = 30;

	public static final String getUserRole(int groupId) {
		if (SUPPERUSERS.contains(groupId))
			return SUPPERUSER;
		else if (ADMINUSERS.contains(groupId))
			return ADMIN;
		else if (TRAINERS.contains(groupId))
			return TRAINER;
		else if (CLIENTS.contains(groupId))
			return CLIENT;
		else if (GUESTS.contains(groupId))
			return GUEST;
		else if (PD_ADMIN.contains(groupId))
			return PDADMIN;
		else
			return INVALID_USER;
	}

	public static final String buildUpdateHistory(Object field, Object previous, Object updated) {
		JSONObject json = new JSONObject();
		json.put(HISTORY_AFFECTED_FIELD, field);
		json.put(HISTORY_PREVIOUS_VALUE, previous);
		json.put(HISTORY_UPDATED_VALUE, updated);
		return json.toString();
	}

	public enum QUESTION_ELEMENTTYPE
	{
		input,label,button,select
	}
}
