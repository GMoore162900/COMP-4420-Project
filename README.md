# COMP-4420 IT Inventory Management System

## Project Description

The IT Inventory Management System is a Java CRUD application for managing
electronic devices across a college campus. The system provides a simple
graphical user interface through which authorized university users can sign
in, identify their department, and view or manage devices assigned to them.

The inventory can include laptops, desktop computers, smart boards, laptop
carts, printers, and networking equipment such as routers and switches. 
Each device record can be created, viewed, updated, or deleted by an authorized user or inventory administrator. 
Device records should include information such as the asset identifier, device type, manufacturer, model, location,
department, assigned user, checkout date, rental due date, and current status.

Users can assign available devices to themselves and update the status of
devices they are responsible for. Supported device statuses are `Active`,
`Repairable`, and `Broken`. The system should preserve assignment and status
information so that the college can track equipment throughout its lifecycle.

Security and eligibility checks are part of the application workflow. A user
must provide valid university credentials, including an email address ending
in `westga.edu`, before accessing protected features. Before a device can be
assigned, the system must also verify that the user does not already have a
device with a rental due date that has passed. Invalid credentials, overdue
assignments, unavailable devices, and unauthorized actions should be rejected
with a clear message in the GUI.

## Product Backlog

The backlog is prioritized from highest priority (`1`) to lowest priority.
Each item is written as a user story using the format from the sample product
backlog.

| Priority | As a... | I want to... | So that... |
| --- | --- | --- | --- |
| 1 | University User | sign in with my university email and password | I can securely access the inventory system |
| 1 | University User | have the system validate that my email ends in `westga.edu` | only users with valid university credentials can use protected features |
| 1 | University User | view my department and provide it when signing in | my device assignments can be associated with the correct department |
| 1 | University User | view available devices | I can select equipment that is not already assigned or unavailable |
| 1 | University User | assign an available device to myself | I can check out equipment for college work |
| 1 | University User | be prevented from assigning a device when I have an overdue assignment | equipment can be returned before I receive another device |
| 1 | University User | see a clear message when an assignment is rejected | I understand why I cannot check out the device |
| 1 | Inventory Administrator | create a device record | new equipment can be added to the campus inventory |
| 1 | Inventory Administrator | view device records | I can track the equipment owned by the college |
| 1 | Inventory Administrator | update a device record | inventory information remains accurate when equipment changes |
| 1 | Inventory Administrator | delete a device record | retired or invalid equipment can be removed from the inventory |
| 1 | University User | view the device assigned to me | I know which equipment is currently under my responsibility |
| 1 | University User | update the status of my assigned device to `Active`, `Repairable`, or `Broken` | the IT department knows the current condition of the equipment |
| 2 | Inventory Administrator | view all assignments by user and department | I can identify who is responsible for each device |
| 2 | Inventory Administrator | record a device asset identifier, type, manufacturer, and model | each device can be uniquely identified and organized |
| 2 | Inventory Administrator | record a device location | I can determine where campus equipment is being used |
| 2 | Inventory Administrator | record a checkout date and rental due date | assignment periods can be monitored |
| 2 | Inventory Administrator | filter devices by type, department, location, or status | I can find relevant equipment quickly |
| 2 | Inventory Administrator | mark a device as unavailable when it is assigned, repairable, or broken | users cannot select equipment that should not be checked out |
| 2 | University User | return a device | the device can become available for another eligible user |
| 2 | Inventory Administrator | view overdue assignments | I can follow up with users who have not returned equipment on time |
| 2 | Inventory Administrator | update a device's assignment details | changes in responsibility or due dates are recorded accurately |
| 3 | University User | update my password | I can keep my account secure |
| 3 | University User | sign out of the application | my account cannot be accessed by the next person using the computer |
| 3 | System Administrator | assign roles to users | inventory-management permissions are limited to authorized staff |
| 3 | System Administrator | restrict create, update, and delete actions to authorized roles | inventory records cannot be changed by unauthorized users |
| 3 | Inventory Administrator | view an audit history of device assignments and status changes | I can review how inventory records have changed over time |
| 3 | Inventory Administrator | receive a warning when a due date is approaching | I can contact a user before an assignment becomes overdue |
| 4 | Inventory Administrator | export inventory and assignment information | I can use the data for reports and departmental records |
| 4 | University User | receive confirmation after assigning or returning a device | I know that the transaction was completed successfully |
| 4 | University User | receive validation messages for missing or invalid form fields | I can correct my information before submitting it |

## Device Categories

- Laptops
- Desktop computers
- Smart boards
- Laptop carts
- Printers
- Routers
- Network switches
- Other approved campus electronics

## Core Rules

- Only university email addresses ending in `westga.edu` may pass email validation.
- A user with an overdue device assignment may not assign another device until the overdue device is returned or resolved by an authorized administrator.
- Only available devices may be assigned to a user.
- A device's status must be one of `Active`, `Repairable`, or `Broken`.
- Protected inventory actions require authentication and the appropriate user role.