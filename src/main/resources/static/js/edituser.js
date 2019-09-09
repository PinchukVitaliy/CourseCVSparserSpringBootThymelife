  $("#choose-file").change(function() {
          filename = this.files[0].name;
          $("#image-name").text(filename);
        });