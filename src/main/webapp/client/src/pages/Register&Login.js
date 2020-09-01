import React, { Component } from "react";
import {
    MDBContainer,
    MDBRow,
    MDBCol,
    MDBTabPane,
    MDBTabContent,
    MDBNav,
    MDBNavItem,
    MDBNavLink,
    MDBIcon,
    MDBInput,
    MDBBtn,
    MDBBox
} from
        "mdbreact";
import BasicPage from "../layouts/BasicPage";

class LoginRegisterTabs extends React.Component {

    state = {
        activeItemJustified: "Login"
    }

    toggleJustified = tab => e => {
        if (this.state.activeItemJustified !== tab) {
            this.setState({
                activeItemJustified: tab
            });
        }
    };

    render() {
        return (
            <MDBContainer  style={{padding: "65px"}}>
                <MDBNav tabs className="nav-justified" color='indigo'>
                    <MDBNavItem>
                        <MDBNavLink link to="#" active={this.state.activeItemJustified === "Login"} onClick={this.toggleJustified("Login")} role="tab" >
                            Login
                        </MDBNavLink>
                    </MDBNavItem>
                    <MDBNavItem>
                        <MDBNavLink link to="#" active={this.state.activeItemJustified === "Register"} onClick={this.toggleJustified("Register")} role="tab" >
                            Register
                        </MDBNavLink>
                    </MDBNavItem>
                </MDBNav>
                <MDBTabContent
                    className="card"
                    style={{
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                    }}
                    activeItem={this.state.activeItemJustified}


                >
                    <MDBTabPane tabId="Login" role="tabpanel">
                        <LoginForm />
                    </MDBTabPane>
                    <MDBTabPane tabId="Register" role="tabpanel">
                        <RegisterForm />
                    </MDBTabPane>
                </MDBTabContent>
            </MDBContainer>
        );
    }
}



const LoginForm = () => {
    return (
        <MDBContainer>
            <MDBRow>
                <MDBCol md="12">
                    <form>
                        <div className="grey-text">
                            <MDBInput label="Type your email" icon="envelope" group type="email" validate error="wrong"
                                      success="right" />
                            <MDBInput label="Type your password" icon="lock" group type="password" validate />
                        </div>
                        <div className="text-center">
                            <MDBBtn color="info">Login</MDBBtn>
                        </div>
                    </form>
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    );
};


const RegisterForm = () => {
    return (
        <MDBContainer>
            <MDBRow>
                <MDBCol md="12">
                    <form>
                        <div className="grey-text">
                            <MDBInput label="Your email" icon="envelope" group type="email" validate error="wrong"
                                      success="right" />
                            <MDBInput label="Your password" icon="lock" group type="password" validate />
                            <MDBInput label="Confirm your password" icon="exclamation-triangle" group type="password" validate
                                      error="wrong" success="right" />
                        </div>
                        <div className="text-center">
                            <MDBBtn color="info">Register</MDBBtn>
                        </div>
                    </form>
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    );
};


class LoginRegisterPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            collapse: false
        };
    }

    render() {
        return(<BasicPage isLoggedIn={this.props.isLoggedIn} isAdmin={this.props.isAdmin} isHomePage={false}  mainPart={<LoginRegisterTabs/>}/>
            );
         }
    }



// export default RegisterForm;
//
// export default LoginForm;

export default LoginRegisterPage;
