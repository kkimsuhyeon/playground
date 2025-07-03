
print("MongoDB initialization script running...");

db = db.getSiblingDB("api_log_db");
print("MongoDB database created: api_log_db");

db.createUser({
    user: "user",
    pwd: "user",
    roles: [{ role: "dbOwner", db: "api_log_db" }]
});
print("MongoDB user created: user with dbOwner role on api_log_db");

db.createCollection("logs");
