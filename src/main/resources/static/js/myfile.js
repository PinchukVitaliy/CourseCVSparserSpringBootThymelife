  $("#choose-file").change(function() {
          filename = this.files[0].name;
          $("#image-name").text(filename);
        });

        function copyLink(id) {
                var copyText = document.getElementById(id);
                copyText.select();
                document.execCommand("copy");
         }