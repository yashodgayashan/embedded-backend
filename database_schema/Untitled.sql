CREATE TABLE node (
	nodeId int primary key,
    parentNodeId int,
    createdUserId int,
    disabled int(1) default 0,
    lastModifiedUser int,
    lastModifiedDateTime timestamp default current_timestamp
);

CREATE TABLE sensor (
    sensorId int primary key,
    sensorName varchar(255),
    sensorDiscription varchar(255),
    dataType varchar(255),
    dataSize varchar(255),
    sensingRange varchar(255),
    technology varchar(255),
    workingVoltage varchar(255),
    dimensions varchar(255),
    specialFact varchar(255),
    sensorImageURL varchar(255),
    disabled int(1) default 0,
    lastModifiedUser int,
    lastModifiedDateTime timestamp default current_timestamp
);

create table nodeSensor (
    nodeSensorId int primary key,
    nodeId int,
    sensorId int,
    disabled int(1) default 0,
    lastModifiedUser int,
    lastModifiedDateTime timestamp default current_timestamp,
    foreign key (nodeId) references node(nodeId),
    foreign key (sensorId) references sensor(sensorId)
);

create table data_1 (
	dataId int primary key,
    nodeId int,
    data1 float,
    data2 float,
    data3 float,
    disabled int default 0,
    validated int default 1,
    savedDateTime timestamp default current_timestamp,
    foreign key (nodeId) references node(nodeId)
);

create table data_2 (
	dataId int primary key,
    nodeId int,
    data1 float,
    data2 float,
    data3 float,
    disabled int default 0,
    validated int default 1,
    savedDateTime timestamp default current_timestamp,
    foreign key (nodeId) references node(nodeId)
);

create table data_3 (
	dataId int primary key,
    nodeId int,
    data1 float,
    data2 float,
    disabled int default 0,
    validated int default 1,
    savedDateTime timestamp default current_timestamp,
    foreign key (nodeId) references node(nodeId)
);


