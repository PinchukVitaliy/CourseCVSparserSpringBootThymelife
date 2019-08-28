 $('.selectpicker').selectpicker();
    function openModal() {
             $("#myModal").modal();
         }

    function delRows(){
        var x = document.getElementById("delrows");
        var parent = document.getElementById("parentDiv");
        parent.innerHTML = '';
        var optionVal = [];
        for (i = 0; i < x.length; i++) {
             if (x.options[i].selected){
                    optionVal.push(x.options[i].text);
                    var input=document.createElement('input');
                    input.type = 'hidden';
                    input.name = 'deleterows';
                    input.value= x.options[i].text;
                    var label = document.createElement('label');
                    label.innerHTML = x.options[i].text;
                    var br = document.createElement('br');
                    parent.appendChild(input);
                    parent.appendChild(label);
                    parent.appendChild(br);
                }
        }
        if(optionVal.length !== 0){
           $("#myModalDelRows").modal();
        }
    }


        function delColums(){
        var x = document.getElementById("delcolums");
        var parent = document.getElementById("parentDivColum");
        parent.innerHTML = '';
        var optionVal = [];
        for (i = 0; i < x.length; i++) {
             if (x.options[i].selected){
                    optionVal.push(x.options[i].text);
                    var input=document.createElement('input');
                    input.type = 'hidden';
                    input.name = 'deletecolums';
                    input.value= x.options[i].text;
                    parent.appendChild(input);
                }
        }
        if(optionVal.length !== 0){
        var label = document.createElement('label');
                    label.innerHTML = optionVal;
                    parent.appendChild(label);
           $("#myModalDelcolums").modal();
        }
    }

