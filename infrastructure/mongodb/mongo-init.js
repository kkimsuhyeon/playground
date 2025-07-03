
print("MongoDB initialization script running...");

db = db.getSiblingDB("log_db");
print("MongoDB database created: log_db");

db.createUser({
    user: process.env.MONGO_INITDB_LOG_USER,
    pwd: process.env.MONGO_INITDB_LOG_PASSWORD,
    roles: [{ role: "dbOwner", db: "log_db" }]
});
print("MongoDB user created: user with dbOwner role on log_db");

db.createCollection("logs");
