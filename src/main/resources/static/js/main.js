function deleteHol(id) {
        $.ajax({
            url: "/deleteHol/" + id,
            type: "DELETE",
            contentType: "application/json",
            success: function(response) {
                location.reload();
            },
            error: function(xhr, status, error) {
                alert('테이블에 주문이 있습니다.');
                console.error("Error: " + error);
            }
        });
}

function addHol() {
    $.ajax({
        url: "/addHol",
        type: "POST",
        contentType: "application/json",
        success: function(response) {
            location.reload();
        },
        error: function(xhr, status, error) {
            console.error("Error: " + error);
        }
    });
}