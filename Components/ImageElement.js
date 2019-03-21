import React, { Component } from 'react';
import { StyleSheet, Image } from 'react-native';

export default class ImageElement extends Component {
	constructor(props) {
		super(props);
	}

	render() {
		return <Image source={{ uri: this.props.imgSource }} style={styles.image} />;
	}
}

const styles = StyleSheet.create({
	image: {
		flex: 1,
		width: null,
		alignSelf: 'stretch'
	}
});
