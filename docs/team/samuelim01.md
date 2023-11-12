---
  layout: default.md
  title: "Samuel Lim's Project Portfolio Page"
---

### Project: WedLog

WedLog is a desktop application created for partners getting married that helps them manage the guests and vendors involved in the wedding. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC. This project is based on [AddressBook - Level 3](https://se-education.org/addressbook-level3/).

Given below are my contributions to the project.

* **New Feature**: Modified some fields to be optional for guests and vendors.
  * What it does: allows the user to leave optional fields empty for guests and vendors.
  * Justification: This feature improves the product significantly because a user may not want to specify all the fields of a guest or vendor, and the app should provide the flexibility for the user to do so.
  * Credits: Partially adapted from [AY2324S1-CS2103T-W08-3](https://github.com/AY2324S1-CS2103T-W08-3/tp/).

* **New Feature**: Added the ability to edit vendors.
  * What it does: allows the user to edit fields of a vendor.
  * Justification: This feature improves the product significantly because a user may want to modify vendor information and the app should provide an easy way to do so without having to remove and add back the vendor.
  * Highlights: The implementation was challenging because vendors have optional fields which can be removed by using empty prefixes in the edit command. This required a careful consideration of design alternatives.

* **New Feature**: Added the ability to undo/redo previous commands.
  * What it does: allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
  * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.
  * Credits: Adapted from [AddressBook Level-4](https://github.com/se-edu/addressbook-level4).

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=samuelim01&breakdown=true)

* **Project management**:
  * Renamed `seedu` package to `wedlog` : [\#96](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/96)
  * Managed releases `v1.1` - `v1.1.1` (2 releases: [1](https://github.com/AY2324S1-CS2103T-F11-2/tp/releases/tag/v1.1), [2](https://github.com/AY2324S1-CS2103T-F11-2/tp/releases/tag/v1.1.1)) on GitHub

* **Documentation**:
  * User Guide: 
    * Added documentation for the features `vendor add` and `vendor delete`: [\#40](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/40/)
    * Added documentation for the features `undo` and `redo`: [\#121](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/121)
    * Added documentation for the feature `vendor edit`: [\#128](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/128)
    * Added cosmetic tweaks to the structure: [\#154](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/154), [\#162](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/162)
  * Developer Guide:
    * Added non-functional requirements: [\#39](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/39)
    * Updated Logic, Model, Storage component sections: [\#112](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/112)
    * Added implementation details for the feature `edit`: [\#149](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/149)
    * Updated Appendix B: [\#262](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/262)

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#116](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/116), [\#127](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/127), [\#129](https://github.com/AY2324S1-CS2103T-F11-2/tp/pull/129) 
  * Reported [bugs and suggestions](https://github.com/samuelim01/ped/issues) for other teams
