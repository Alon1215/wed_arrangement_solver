import {MDBCollapse, MDBNavbar, MDBNavbarBrand, MDBNavbarNav, MDBNavbarToggler, MDBNavItem, MDBNavLink} from "mdbreact";
import {BrowserRouter as Router} from "react-router-dom";
import React, {Component} from "react";
import PropTypes from "prop-types";
import HomePage from "../pages/HomePage";

class NavBar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isWideEnough: false,
        };

    }

    render() {

        return (
            <MDBNavbar color="info-color" fixed="top" dark expand="md" scrolling transparent>
                <MDBNavbarBrand href="/">
                    <strong>Wed</strong>
                </MDBNavbarBrand>
                {!this.state.isWideEnough && <MDBNavbarToggler onClick={this.onClick}/>}
                <MDBCollapse isOpen={this.state.collapse} navbar>
                    <MDBNavbarNav left>
                        <MDBNavItem active>
                            <MDBNavLink to="#">Home</MDBNavLink>
                        </MDBNavItem>
                        <MDBNavItem>
                            <MDBNavLink to="#">Register</MDBNavLink>
                        </MDBNavItem>
                        <MDBNavItem>
                            <MDBNavLink to="#">Login</MDBNavLink>
                        </MDBNavItem>
                        <MDBNavItem>
                            <MDBNavLink to="#">More About Us</MDBNavLink>
                        </MDBNavItem>
                    </MDBNavbarNav>
                </MDBCollapse>
            </MDBNavbar>);
    }
}

// PropTypes
NavBar.propTypes = {
    isLoggedIn: PropTypes.func.isRequired,
    isAdmin: PropTypes.func.isRequired,
    isHomePage: PropTypes.bool.isRequired,
};

export default NavBar;
