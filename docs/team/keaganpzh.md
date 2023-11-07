---
  layout: default.md
  title: "Keagan Pang's Project Portfolio Page"
---

### Project: WedLog
WedLog is a desktop application created for partners getting married that helps them manage the guests and vendors involved in the wedding. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC. This project is based on [AddressBook - Level 3](https://se-education.org/addressbook-level3/).

Given below are my contributions to the project.

### Features
1. **New Feature**: Added the ability to track guests.
   * What it does: allows the user to add, delete, edit, find and list guests. Guests have a name, phone number, email, address, tags, RSVP status, dietary requirements, and a table number.
   * Justification: This is a core feature of WedLog as it allows the user to manage their guests and their associated information.
   * Credits: `Guest` class was adapted from `Person` class in AddressBook Level 3.

2. **New Feature**: Added the ability to track a guest's RSVP status.
   * What it does: allows the user to set a guest's RSVP status to either "yes", "no", or "unknown".
   * Justification: This feature allows users to keep track of their guests' RSVP status. By limiting the possible RSVP statuses to "yes", "no", or "unknown", the user can easily see which guests coming, not coming, or unsure yet.
   * Highlights: When displaying the RSVP status of a guest in the UI, I decided to make it colour coded. This makes it easier for the user to see the RSVP status of a guest at a glance.

3. **New Feature**: Added the ability to track a guest's dietary requirements.
   * What it does: allows the user to set a guest's dietary requirements to any number of values.
   * Justification: This feature allows users to keep track of their guests' dietary requirements. By allowing the user to set any number of dietary requirements for a guest, there is flexibility in organising the lists of guests.

4. **New Feature**: Added a panel in the UI to display statistics of the guest list.
   * What it does: allows the user to have easy access to guest information such as the proportion of guests' RSVP statuses, and a list of unique dietary requirements.
   * Justification: This feature allows users to easily see the statistics of their guest list. This is useful for the user to see how many guests are coming, not coming, or unsure yet. It is also useful for the user to see the dietary requirements of their guests, so that they can organise the catering accordingly.
   * Highlights: One challenge faced was making each component react and change accordingly after each command execution. This required rebuilding parts of the UI after each command entered. Another challenge was to pass the correct data to the UI components. This required the use of the `RsvpStatistics` and `DietaryRequirementStatistics` classes to store the data.

5. **New Feature**: Reworked the UI to suit the two-list idealogy.
   * What it does: displays two lists side by side, one for guests, and one for vendors. The statistics panel with the logo is displayed alongside the two lists.
   * Justification: originally, AB3 is designed taller than wide. By making WedLog wider than tall, it allows the user to see more information at a glance, and is more suitable for the two-list idealogy.


### Code Contributed
[RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=keaganpzh&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)

### Documentation
* **User Guide**:
* **Developer Guide**:
    * Updated Glossary and Appendix. [PR #42](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/42)
    * Created new class diagrams for UI and Guest. [PR #144](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/144)
    * Added documentation for tacking of guests and vendors. [PR #144](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/144)
    * Added "Planned Enhancements" section.

### Contributions to team tasks
* **Project Management**:
    * Created team repository.
    * Migrated team documentation to MarkBind.
    * Set up Codecov to track code coverage. [\#1](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/1)
    * Managed releases `v1.2`, `v1.3(trial)`, `v1.3`.

### Contributions beyond the Project Team
* **PE-D**:
    * Identified and published [9 issues](https://github.com/keaganpzh/ped) to another team's repository.
  
