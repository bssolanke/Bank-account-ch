# Bank account challenge

To launch the service in production it requires adding or implementing following:
* add user authorization;
* implement DAO layer to persist data into some storage;
* extend service's API to minimal set of usual bank's account operations. For example to add following operations:
  - account deposit;
  - account withdraw;
  - account operations history with search capability;
  - lock/unlock account;

If we are developing the service as a part of more complex system with microservice approach I would proposed extract NotificationService into separate service that sends specific notifications triggered by coresponding events.