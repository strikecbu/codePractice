db.createCollection("posts", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["title", "text"],
      properties: {
        title: {
          bsonType: "string",
          description: "title should not be empty",
        },
        text: {
          bsonType: "string",
          description: "text should not be empty",
        },
      },
    },
  },
});

db.runCommand({
  collMod: "posts",
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["title", "text", "author", "comments"],
      properties: {
        title: {
          bsonType: "string",
          description: "title should not be empty",
        },
        text: {
          bsonType: "string",
          description: "text should not be empty",
        },
        author: {
          bsonType: "objectId",
          description: "must need a author",
        },
        comments: {
          bsonType: "array",
          description: "must need comments array",
          items: {
            required: ["text", "author"],
            properties: {
              text: {
                bsonType: "string",
                description: "text should not be empty",
              },
              author: {
                bsonType: "objectId",
                description: "must need a author",
              },
            },
          },
        },
      },
    },
  },
});

db.posts.insertOne({ 
    title: "First post", 
    text: "this is a good day",
    author: ObjectId("62bb1e0e6ee0287c4cb7a2c2"),
    comments: [{
        author: ObjectId("62bb1e0e6ee0287c4cb7a2c3"),
        text: "good post!"
    }]
});
