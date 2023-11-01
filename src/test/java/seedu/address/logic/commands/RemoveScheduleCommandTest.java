package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.timetable.Cca;
import seedu.address.model.person.timetable.DatedEvent;
import seedu.address.model.person.timetable.Module;
import seedu.address.model.user.User;
import seedu.address.model.user.UserData;
import seedu.address.model.user.UserPrefs;
import seedu.address.testutil.UserBuilder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class RemoveScheduleCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserData());

    @Test
    public void execute_validCca_success() {
        User newUser = new UserBuilder().build();
        model.setUser(newUser);
        model.getUser().getSchedule().addCca(new Cca("Basketball", "Monday 1800 2000"));
        RemoveScheduleCommand removeScheduleCommand = new RemoveScheduleCommand("Basketball",
                "cca");
        String expectedMessage = "CCA 'Basketball' deleted from your calendar!";
        Model expectedModel = new ModelManager(model.getAddressBook(),
                new UserPrefs(), new UserData());
        expectedModel.setUser(newUser);
        expectedModel.getUser().getSchedule().addCca(new Cca("Basketball", "Monday 1800 2000"));

        assertCommandSuccess(removeScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCca_friend_success() {
        User newUser = new UserBuilder().build();
        model.setUser(newUser);
        Person friend = model.getFilteredPersonList().get(0);
        friend.getSchedule().addCca(new Cca("Basketball", "Monday 1800 2000"));
        RemoveScheduleCommand removeScheduleCommand = new RemoveScheduleCommand("Basketball",
                "cca", Index.fromZeroBased(0));
        String expectedMessage = "CCA 'Basketball' deleted from Alice Pauline's calendar!";
        Model expectedModel = new ModelManager(model.getAddressBook(),
                new UserPrefs(), new UserData());
        expectedModel.setUser(newUser);
        Person expectedFriend = expectedModel.getFilteredPersonList().get(0);
        expectedFriend.getSchedule().addCca(new Cca("Basketball", "Monday 1800 2000"));

        assertCommandSuccess(removeScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validModule_success() {
        User newUser = new UserBuilder().build();
        model.setUser(newUser);
        model.getUser().getSchedule().addModule(new Module("CS2103", "Monday 1800 2000"));
        RemoveScheduleCommand removeScheduleCommand = new RemoveScheduleCommand("CS2103",
                "module");
        String expectedMessage = "Module 'CS2103' deleted from your calendar!";
        Model expectedModel = new ModelManager(model.getAddressBook(),
                new UserPrefs(), new UserData());
        expectedModel.setUser(newUser);
        expectedModel.getUser().getSchedule().addModule(new Module("CS2103", "Monday 1800 2000"));

        assertCommandSuccess(removeScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validModule_friend_success() {
        User newUser = new UserBuilder().build();
        model.setUser(newUser);
        Person friend = model.getFilteredPersonList().get(0);
        friend.getSchedule().addModule(new Module("CS2103", "Monday 1800 2000"));
        RemoveScheduleCommand removeScheduleCommand = new RemoveScheduleCommand("CS2103",
                "module", Index.fromZeroBased(0));
        String expectedMessage = "Module 'CS2103' deleted from Alice Pauline's calendar!";
        Model expectedModel = new ModelManager(model.getAddressBook(),
                new UserPrefs(), new UserData());
        expectedModel.setUser(newUser);
        Person expectedFriend = expectedModel.getFilteredPersonList().get(0);
        expectedFriend.getSchedule().addModule(new Module("CS2103", "Monday 1800 2000"));

        assertCommandSuccess(removeScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventType_failure() {
        User newUser = new UserBuilder().build();
        model.setUser(newUser);
        model.getUser().getSchedule().addModule(new Module("CS2103", "Monday 1800 2000"));
        RemoveScheduleCommand removeScheduleCommand = new RemoveScheduleCommand("CS2103",
                "sleep");

        String expectedMessage = "Invalid event type or name!\n"
                + "Event type must either be 'cca' or 'module' and event must be in schedule!\n";

        assertCommandFailure(removeScheduleCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidEventName_failure() {
        User newUser = new UserBuilder().build();
        model.setUser(newUser);
        model.getUser().getSchedule().addModule(new Module("CS2103", "Monday 1800 2000"));
        RemoveScheduleCommand removeScheduleCommand = new RemoveScheduleCommand("CS2101",
                "module");

        String expectedMessage = "Invalid event type or name!\n"
                + "Event type must either be 'cca' or 'module' and event must be in schedule!\n";

        assertCommandFailure(removeScheduleCommand, model, expectedMessage);
    }



    @Test
    public void listEvents_success() {
        RemoveScheduleCommand removeScheduleCommand = new RemoveScheduleCommand("CS2103",
                "module");
        ArrayList<DatedEvent> events = new ArrayList<>();
        events.add(DatedEvent.newDatedEvent("CS2103 Meeting 2023-10-10 1030 1130 y"));
        events.add(DatedEvent.newDatedEvent("Walk Dog 2023-10-10 1030 1130 n"));
        events.add(DatedEvent.newDatedEvent("Competitive sleeping 2023-10-10 1030 1130 y"));
        String expectedMessage = "DatedEvent: [CS2103 Meeting] on 2023-10-10 [Tuesday 1030 1130] Reminder: Yes\n"
                + "DatedEvent: [Walk Dog] on 2023-10-10 [Tuesday 1030 1130] Reminder: No\n"
                + "DatedEvent: [Competitive sleeping] on 2023-10-10 [Tuesday 1030 1130] Reminder: Yes\n";
        assertEquals(expectedMessage, removeScheduleCommand.listEvents(events));
    }

    @Test
    public void listEvents_empty() {
        RemoveScheduleCommand removeScheduleCommand = new RemoveScheduleCommand("CS2103",
                "module");
        ArrayList<DatedEvent> events = new ArrayList<>();
        String expectedMessage = "";
        assertEquals(expectedMessage, removeScheduleCommand.listEvents(events));
    }

    // reformat string in RemoveScheduleCommand above

    // can consider cases where type is correct but event inside schedule for exception in default

    // can consider throwing exception in listEvents if wrong type of events eg Cca
    // if added lmk so that i can test the catch exception part

}
