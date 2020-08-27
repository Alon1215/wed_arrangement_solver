import React from "react";
import {MDBContainer, MDBFooter} from 'mdbreact';


class Footer extends React.Component {
    render() {

        return (
                    <MDBFooter color="info-color" className="font-small ">
                        <div className="footer-copyright text-center">
                            <MDBContainer fluid>
                                &copy; {new Date().getFullYear()} Copyright: <a href="https://github.com/yodatk/wed"> Wed </a>
                            </MDBContainer>
                        </div>
                    </MDBFooter>
        );
    }
}



export default Footer;