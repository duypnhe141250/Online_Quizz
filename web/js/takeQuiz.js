function getResult() {
    let rs = "";
    let answers = document.getElementsByClassName("option");
    //get all answers that user selected
    for (let i = 0; i < answers.length; i++) {
        //get checkBox of answers was checked
        if (answers[i].checked) {
            rs += answers[i].value + " ";
        }
    }
    return rs;
}

const result = document.getElementById("result");
const resultForm = document.getElementById("result_form");
function submitResult() {
    result.value = getResult();
    resultForm.submit();
}

const timeLabel = document.getElementById("time");
function timer(duration, timeLabel) {
    let minutes, seconds;
    const radix = 10;
    //this function will excecute after 1000 millliseconds
    setInterval(() => {
        //minutes equal duration divide to 60
        minutes = parseInt(duration / 60, radix);
        //seconds equal duration mod to 60
        seconds = parseInt(duration % 60, radix);
        
        //display 0 before minutes if it less than 10
        minutes = minutes < 10 ? "0" + minutes : minutes;
        //display 0 before seconds if it less than 10
        seconds = seconds < 10 ? "0" + seconds : seconds;

        timeLabel.textContent = minutes + ":" + seconds;

        //submit result when out of time
        if (duration === 0) {
            //submitResult();
            return;
        }
        duration--;
    }, 1000);
}

const questions = document.getElementsByClassName("take_question");
window.onload = function () {
    //display block the first question when web loaded all the questions 
    questions[0].classList.remove("none");
    questions[0].classList.add("block");
    let timelabel = timeLabel.textContent;
    let duration = parseInt(timelabel.substr(0, 3), 10) * 60 + parseInt(timelabel.substr(-2), 10);
    timer(duration - 1, timeLabel);
};

const nextBtn = document.getElementById("nextBtn");
//set default the first question is 1
let currentIndex = 1;
nextBtn.onclick = function () {
    //submit result when currentIndex is equal to position of last question 
    if (currentIndex === questions.length) {
        submitResult();
    } else {
        //set text for button when currentIndex is equal to positon of before last question
        if (currentIndex === questions.length - 1) {
            nextBtn.textContent = "Finish";
        }
        questions[currentIndex -1].classList.remove("block");
        questions[currentIndex -1].classList.add("none");
        
        questions[currentIndex].classList.remove("none");
        questions[currentIndex].classList.add("block");
        
        ++currentIndex;
    }

};