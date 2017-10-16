/**
 * Created by os199 on 12.10.2017.
 */
function requestRatingDocument() {
    $.ajax({
        url : 'ratingcontrol',
        type: 'GET',
        data: {
            year: 4,
            semester: 1
        },
        success : function(group) {
            $("#group").append(group);
            // for (var i = 0; i<group.students.length; i++){
            //     $("#group").append('<p>'+group.students[i].surname);
            // }
            // for (var i = 0; i<group.subjects.length; i++){
            //     $("#group").append('<p>'+group.subjects[i].name);
            // }
        }
    });
}
