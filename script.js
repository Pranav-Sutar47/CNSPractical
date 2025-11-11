
async function signup(){
    const email = document.getElementById('signupEmail');
    const psw = document.getElementById('signupPsw');

    const emailValue = email.value.trim();
    const pswValue = psw.value.trim();

    if(!emailValue || !pswValue){
        alert("Please fill all the fields");
        email.value = "";
        psw.value = "";
    }

    const hashPsw = await hash(pswValue,"THISISENCRYPTION");
    console.log(hashPsw)

    // const v = checkPsw(pswValue);
    // console.log('hi:'+v);

    if(!checkPsw(pswValue)){
        alert("Invalid Password");
        return;
    }

    localStorage.setItem("email",emailValue);
    localStorage.setItem("psw",hashPsw);
    localStorage.setItem("attempt",5);
    alert("Signup Successful");

    document.getElementById('login').classList.remove('hidden');
    document.getElementById('signup').classList.add('hidden');
}

async function hash(msg,salt){
    const encoder = new TextEncoder();
    const data = encoder.encode(msg+salt);
    const hashBuffer = await crypto.subtle.digest("SHA-256",data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hash = hashArray.map(b=>b.toString(16).padStart(2,"0")).join('');
    return hash;
}

async function login(){
    const email = document.getElementById('loginEmail');
    const psw = document.getElementById('loginPsw');

    const emailValue = email.value.trim();
    const pswValue = psw.value.trim();

    // const hashPsw = await genHash(pswValue,"THISISENCRYPTION");
    console.log(hashPsw);
    if(!emailValue || !pswValue){
        alert("Please fill all the fields");
        email.value = "";
        psw.value = "";
    }



    const originalEmail = localStorage.getItem('email');
    const originalPsw = localStorage.getItem('psw');
    const attempt = localStorage.getItem('attempt');
    if(attempt <= 0){
        alert('Try after some time');
        document.getElementById('loginBtn').disabled = true;

        setTimeout(()=>{
            document.getElementById('loginBtn').disabled = false;
            localStorage.setItem('attempt',3);
            alert('You can try now');
        },5000);

        return;
    }
    if(emailValue !== originalEmail || originalPsw !== hashPsw){
        alert('Invalid credentials');
        localStorage.setItem('attempt',attempt-1);
        return;
    }
    email.value = "";
    psw.value = "";

    document.getElementById('login').classList.add('hidden');
    document.getElementById('home').classList.remove('hidden');
}


function checkPsw(msg){
    const pattern = /[A-Z]/.test(msg) && /[a-z]/.test(msg) && /\d/.test(msg) && /^[A-Za-z0-9]/.test(msg) && /[@!#$]/.test(msg) && msg.length >= 8;
    return pattern;
}
