/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import { Provider as PaperProvider } from 'react-native-paper';
import HomeScreen from './Views/HomeScreen';

export default class App extends Component {
	render() {
		return (
			<PaperProvider>
				<HomeScreen />
			</PaperProvider>
		);
	}
}
