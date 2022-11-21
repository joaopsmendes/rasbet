import React,{useState} from "react";

function Login() {
    const[email,setEmail]=useState('')
    const[password,setPassword]=useState('')




    const login =  async () =>{
        /*
        const response = await fetch(URL , {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
            mode: 'no-cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            headers: {
                'Content-Type': 'application/json'
            },
            redirect: 'follow', // manual, *follow, error
            body: JSON.stringify({
                email: email,
                password : password,
            })
        });
        console.log(response.json());
        */
        const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
              },
            body: JSON.stringify({
                "email": email, 
                "password": password
            }),
        });
        if(response.status === 200){
            console.log("Login Success")
        }
        else{
            console.log("Login Failed")
        }
        console.log(response.status);
    }

    return (
        <div className="Login">
            <input placeholder="Enter your Email" onChange={(e) => (setEmail(e.target.value))}></input>
            <br></br>
            <input placeholder="Enter your Password" onChange={(e) => (setPassword(e.target.value))}></input>
            <div>
                <button type="button" onClick={()=>login()} >Login</button>
            </div>
        </div>

    );
  }

export default Login;
