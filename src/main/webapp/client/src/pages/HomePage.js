import React, {Component} from "react";
import {
    MDBNavbar,
    MDBNavbarBrand,
    MDBNavbarNav,
    MDBNavbarToggler,
    MDBCollapse,
    MDBNavItem,
    MDBNavLink,
    MDBMask,
    MDBView,
    MDBCol, MDBContainer, MDBRow, MDBFooter,
} from 'mdbreact';
import {BrowserRouter as Router} from 'react-router-dom';
import NavBar from "../layouts/NavBar";
import BasicPage from "../layouts/BasicPage";
import PropTypes from "prop-types";


class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            collapse: false
        };
        this.onClick = this.onClick.bind(this);
    }

    onClick() {
        this.setState({
            collapse: !this.state.collapse,
        });
    }

    render() {

        return (<BasicPage isLoggedIn={this.props.isLoggedIn} isAdmin={this.props.isAdmin} isHomePage={true}
                           belowNavBar={<MDBView src="https://live.staticflickr.com/4690/24082885557_eb6f698c97.jpg">
                               <MDBMask className="flex-center flex-column text-white">
                                   <h2>Use Automatic Sitting Plan For Your Wedding </h2>
                                   <h5>Because:</h5>
                                   <p>1. It's easy</p><br/>
                                   <p>2. It's accurate</p>
                                   <p>3.No other reason required</p>

                               </MDBMask>
                           </MDBView>} mainPart={<MDBContainer className="text-center my-5">
            <p align="justify">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt
                ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa
                qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing
                elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis
                nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
                reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
                cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
        </MDBContainer>} footPart={<div/>}/>);
    }
}

// PropTypes
HomePage.propTypes = {
    isLoggedIn: PropTypes.func.isRequired,
    isAdmin: PropTypes.func.isRequired,
    isHomePage: PropTypes.bool.isRequired,
}

export default HomePage;