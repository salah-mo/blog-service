$(document).ready(function () {
    var converter = new showdown.Converter();

    // Handle blog upload form submission
    $('#upload-form').submit(function (event) {
        event.preventDefault();

        var formData = new FormData(this);

        $.ajax({
            url: 'http://localhost:8080/api/blogs/upload',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                alert('Blog post uploaded successfully!');
                $('#upload-form')[0].reset();
            },
            error: function (error) {
                alert('Error uploading blog post.');
                console.error(error);
            }
        });
    });

    // Handle search form submission
    $('#search-form').submit(function (event) {
        event.preventDefault();

        var title = $('#search-title').val();

        $.ajax({
            url: `http://localhost:8080/api/blogs/title/${encodeURIComponent(title)}`,
            type: 'GET',
            success: function (response) {
                var results = $('#search-results');
                results.empty();

                if (response.length === 0) {
                    results.append('<p>No blog posts found.</p>');
                } else {
                    response.forEach(function (blog) {
                        var blogContentHtml = converter.makeHtml(blog.content.map(content => content.data).join("\n"));

                        var blogHtml = `
                            <div class="blog-post">
                                <h3>${blog.title}</h3>
                                <p>${blog.summary}</p>
                                <p><strong>Author ID:</strong> ${blog.author}</p>
                                <p><strong>Created At:</strong> ${new Date(blog.createdAt).toLocaleString()}</p>
                                <p><strong>Keywords:</strong> ${blog.keywords.join(', ')}</p>
                                <div>${blogContentHtml}</div>
                            </div>
                            <hr>
                        `;
                        results.append(blogHtml);

                        // Ensure images have the appropriate styling
                        $('.blog-post img').each(function () {
                            $(this).css({
                                'max-width': '100%',
                                'height': 'auto',
                                'display': 'block',
                                'margin': '10px 0'
                            });
                        });
                    });
                }
            },
            error: function (error) {
                alert('Error retrieving blog posts.');
                console.error(error);
            }
        });
    });
});
