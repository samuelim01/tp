---
layout: default.md
title: "Pan Xinping's Project Portfolio Page"
---

# Overview

WedLog is a desktop application created for partners getting married that helps them manage the guests and vendors involved in the wedding. 
The user interacts with it using a CLI, and it has a GUI created with JavaFX. 
It is written in Java, and has about 10 kLoC. This project is based on [AddressBook - Level 3](https://se-education.org/addressbook-level3/).

Given below are my contributions to the project.

* **New Feature**: Added the ability to add guests and vendors.
    * What it does: Allows users to add new guests and vendors with all the associated fields to WedLog.
    * Justification: This feature is central to the product as users cannot manage guests and vendors without being able to add them.
    * Highlights: The implementation was challenging as guests and vendors had optional fields which users could choose not to fill in.

* **New Feature**: Added the ability to edit guests.
    * What it does: Allows the user to edit fields of a guest.
    * Justification: This feature improves the product significantly because a user may want to modify guest information and the app should provide an easy way to do so without having to remove and add the guest back.
    * Highlights: The implementation was challenging because guests have optional fields which can be removed by using empty prefixes in the edit command. This required a careful consideration of design alternatives.

* **New Feature**: Added the ability to filter guests by fields that stored information as tags (i.e., Dietary Requirements and Tags fields).
    * What it does: Allows users to filter all guests using tag fields.
    * Justification: This feature improves the product significantly as users may want to view all users that belong to a certain category (e.g. "vegans", or "classmates") without searching for them individually.
    * Highlights: 
      * The implementation was challenging as unlike non-tag fields, tag fields can store multiple values (e.g. a guest may have two Dietary Requirements tags). Hence, there was a need to carefully consider use cases to decide on the final design where users can filter via multiple tags.
      * Ensured high test effectiveness and efficiency by applying test heuristics like equivalence partitions to test designs (see `TagPredicateTest` and `GuestDietaryPredicateTest`).

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=p-xp&breakdown=true)

* **Contribution to team tasks**:
    * Organised team meeting agenda based on weekly deliverables and tracked list of TODOs determined during team discussions.
    * Allocated issues identified in PED to team members on GitHub.

* **Documentation**:
    * User Guide:
        * Added documentation for `guest edit` [\#127](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/127)
        * Updated documentation for `guest add` [\#105](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/105), `vendor add` [\#106](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/106), and`guest filter` [\#153](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/153)
    * Developer Guide:
        * Added implementation details of the `add` feature: [\#150](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/150)

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#41](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/41), [\#142](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/142)
    * Reported [17 bugs and suggestions](https://github.com/p-xp/ped/issues) for other teams


