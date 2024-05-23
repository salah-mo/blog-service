# Blog Service

This is a Spring Boot application that provides a RESTful API for uploading and retrieving blog posts. Blog posts are
stored in a MongoDB database. The application allows uploading blog posts in Markdown format, which are then stored with
additional metadata.

## Features

- Upload a blog post with metadata and Markdown content
- Retrieve blog posts by title

## Technologies Used

- Spring Boot
- Spring Data MongoDB
- MongoDB
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- MongoDB

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/salah-mo/blog-service.git
    cd blog-service
    ```

2. Configure MongoDB connection in `src/main/resources/application.properties`:

    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/blogdb
    ```

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    mvn spring-boot:run
    ```

## API Endpoints

### Upload a Blog Post

- **URL:** `/api/blogs/upload`
- **Method:** `POST`
- **Content-Type:** `multipart/form-data`
- **Parameters:**
    - `file` (required): The Markdown file of the blog post.
    - `title` (required): The title of the blog post.
    - `summary` (required): A short summary of the blog post.
    - `image` (required): The filename or URL of the blog post image.
    - `author` (required): The ID of the author.
    - `keywords` (required): Comma-separated keywords associated with the blog post.

- **Example:**

    ```bash
    curl -F "file=@path/to/sample_blog.md" -F "title=Sample Blog Post" -F "summary=This is a sample blog post" -F "image=112.jpg" -F "author=1234567890abcdef" -F "keywords=sample,dummy,data" http://localhost:8080/api/blogs/upload
    ```

### Get Blogs by Title

- **URL:** `/api/blogs/title/{title}`
- **Method:** `GET`
- **Parameters:**
    - `title` (required): The title of the blog post.

- **Example:**

    ```bash
    curl -X GET "http://localhost:8080/api/blogs/title/Sample%20Blog%20Post"
    ```
  
## Database Initialization

To initialize the MongoDB database, follow these steps:

1. Ensure MongoDB is running on your machine. By default, it runs on `mongodb://localhost:27017`.
2. Create the initialization script `initBlogDB.js` with the following content:

    ```javascript
    // Connect to the MongoDB instance
    const conn = new Mongo();
    const db = conn.getDB("blogdb");

    // Create an initial collection and insert a sample document
    db.blogs.insertOne({
        title: "Sample Blog Post",
        image: "sample.jpg",
        summary: "This is a sample blog post",
        content: [
            { type: "text", data: "Lorem ipsum dolor sit amet" },
            { type: "image", data: "image.jpg" }
        ],
        author: ObjectId("1234567890abcdef12345678"),
        createdAt: new Date(),
        keywords: ["sample", "dummy", "data"],
        enabled: true
    });

    // Create indexes for the blogs collection
    db.blogs.createIndex({ title: "text" });
    db.blogs.createIndex({ author: 1 });
    db.blogs.createIndex({ createdAt: -1 });

    // Print a success message
    print("Database and collection initialized successfully.");
    ```

3. Run the script using the `mongo` shell:

    ```bash
    mongo <path_to_initBlogDB.js>
    ```

    Replace `<path_to_initBlogDB.js>` with the actual path to your `initBlogDB.js` file. For example, if the script is in your current directory, you can use:

    ```bash
    mongo initBlogDB.js
    ```

4. Verify the database initialization by connecting to MongoDB and checking the contents:

    ```bash
    mongo
    use blogdb
    show collections
    db.blogs.find().pretty()
    ```

By following these instructions, you will have your MongoDB database initialized and ready for use with the Blog Service application.
