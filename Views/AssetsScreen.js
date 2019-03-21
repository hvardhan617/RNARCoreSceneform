import React, { Component } from 'react';
import { ActivityIndicator, Colors } from 'react-native-paper';
import {
	View,
	Dimensions,
	StyleSheet,
	TouchableWithoutFeedback,
	NativeEventEmitter,
	NativeModules
} from 'react-native';
import ImageElement from '../Components/ImageElement';

const activityStarter = NativeModules.ActivityStarter;

export default class AssetsScreen extends Component {
	constructor(props) {
		super(props);
		this.state = {
			loading: true,
			assets: []
		};
	}

	componentDidMount() {
		//Make list assets poly api Call
		let url =
			'https://poly.googleapis.com/v1/assets?key=AIzaSyCmdrzgJHhkiNa97ZsNxPxCvojRYQRREO0&format=GLTF2&curated=true';
		return fetch(url)
			.then((response) =>
				// console.log('Response from poly list assets api:::' + response.json());
				response.json()
			)
			.then((responseJson) => {
				this.setState({
					loading: false,
					assets: responseJson.assets
				});
			})
			.catch((error) => {
				console.log('Error::' + error);
			});
	}

	launchARActivity = (key, assetId, assetUrl) => {
		console.log('Asset selected:::' + assetId + assetUrl);
		activityStarter.launchArActivity(assetId, assetUrl);
	};

	render() {
		console.log('Loading status:::' + this.state.loading);
		let assets = this.state.assets.map((val, key) => {
			let assetId = val.name.split('/');
			let assetUrl;
			var index;
			for (index = 0; index < val.formats.length; ++index) {
				if (val.formats[index].formatType === 'GLTF' || val.formats[index].formatType === 'GLTF2') {
					assetUrl = val.formats[index].root.url;
				}
			}

			console.log('Thumbnail url:::' + val.thumbnail.url);
			return (
				<TouchableWithoutFeedback
					key={key}
					onPress={() => {
						this.launchARActivity(key, assetId[1], assetUrl);
					}}
				>
					<View style={styles.imageWrap}>
						<ImageElement imgSource={val.thumbnail.url} />
					</View>
				</TouchableWithoutFeedback>
			);
		});
		if (this.state.loading) {
			return (
				<View>
					<ActivityIndicator animating={true} color={Colors.red800} />
				</View>
			);
		} else {
			return <View style={styles.container}>{assets}</View>;
		}
	}
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
		flexDirection: 'row',
		flexWrap: 'wrap',
		backgroundColor: 'white'
	},
	imageWrap: {
		margin: 2,
		padding: 2,
		height: Dimensions.get('window').height / 3 - 12,
		width: Dimensions.get('window').width / 2 - 4,
		backgroundColor: 'white'
	}
});

const eventEmitter = new NativeEventEmitter(activityStarter);
eventEmitter.addListener(activityStarter.MyEventName, (params) => alert(params));
