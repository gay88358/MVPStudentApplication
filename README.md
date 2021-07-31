# MVPStudentApplication
This is a simple application used to demo Model View Presentation and Clean Architecture style.
Package structure is design according to the Clean Architecture style.
- Domin
- Application
  - port
    - in
    - out
- Adapter
  - in
  - out

Model View Presentation is used to isolate ui interaction logic within presentation model, so that we can put test logic into test harness.
Clean Architecture is used to isolate core domain logic from external dependency like database, ui, framework.

Combine MVP with CA, we can easy to test all logic including interaction logic and domain logic.
This knid of flexibility is usefull for complex application, which is means that UI, Domain and Persistence has totally different variation.
So that we could change each of one without affecting others.
