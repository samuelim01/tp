---
layout: default.md
title: "Joe Chua's Project Portfolio Page"
---

### Project: WedLog

WedLog is a desktop application created for partners getting married that helps them manage the guests and vendors involved in the wedding. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC. This project is based on [AddressBook - Level 3](https://se-education.org/addressbook-level3/).

Given below are my contributions to the project.

### Features
1. **New Feature**: Added the ability to parse inputs for all parameters in guest and vendor commands.
    * What it does: Parses the user inputs and returns command to be executed.
    * Justification: This feature improves the product significantly because guest and vendor command inputs can be parsed and commands can be executed accordingly.
    * Highlights: The implementation was challenging because each command had a respective parser class which accepted different types of inputs respectively. This required careful consideration of possible user inputs.
    * Credits: Partially adapted from `Parser` classes in [AY2324S1-CS2103T-W08-3](https://github.com/AY2324S1-CS2103T-W08-3/tp/).

2. **New Feature**: Added the ability to display lists of guests and vendors.
    * What it does: allows the user to view all guests and vendors in a list format.
    * Justification: This feature improves the product significantly because a user may want to have an overarching view of all their guests that will be attending the wedding and the vendors they have in contact.
    * Credits: Partially adapted from `ListCommand` class in [AY2324S1-CS2103T-W08-3](https://github.com/AY2324S1-CS2103T-W08-3/tp/).

3. **New Feature**: Added the ability to filter guests and vendors.
    * What it does: allows the user to filter guest and vendor lists by all non-tag parameters.
    * Justification: This feature improves the product significantly because a user can now filter the guest and vendor list by more parameters, giving them a more customised filter feature.
    * Highlights: The implementation was challenging because each parameter had a respective predicate class which accepted different types of inputs respectively. This required careful consideration of possible user inputs.
    * Credits: Partially adapted from `FindCommand` and `NameContainsKeywordsPredicate` class in [AY2324S1-CS2103T-W08-3](https://github.com/AY2324S1-CS2103T-W08-3/tp/).

4. **New Feature**: Modified the guest class to take in Dietary requirement field (initial implementation).
    * What it does: allows the user to add a dietary requirement to guests.
    * Justification: This feature improves the product significantly because a user can now keep track of dietary requirements for guests.
    * Credits: Partially adapted from `Phone` class in [AY2324S1-CS2103T-W08-3](https://github.com/AY2324S1-CS2103T-W08-3/tp/).

### Code Contributed
[RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=wasjoe1&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)

### Documentation
* User Guide:
    * Added documentation for the features `guest list` and `vendor list`: [\#40](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/40/)
    * Added documentation for the features `guest filter` and `vendor filter`: [\#121](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/121)
    * Added documentation for dietary requirements field: [\#98](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/98)
    * Vetted UG before PED: [\#174](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/174), [\#175](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/175)
* Developer Guide:
    * Added use case section: [\#38](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/38)
    * Added implementation details for the feature `filter`: [\#145](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/145)

### Contributions to team tasks
* Managed release `v1.3` (https://github.com/AY2324S1-CS2103T-F11-2/tp/releases/tag/v1.3) on GitHub.

### Community
* PRs reviewed (with non-trivial review comments): [#\164](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/164), [#\153](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/153)
* Reported [13 bugs and suggestions](https://github.com/wasjoe1/ped/issues) for other teams
