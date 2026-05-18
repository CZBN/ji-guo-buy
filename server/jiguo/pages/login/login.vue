<template>
	<view class="status_bar"></view>
	<view class="login-wrap">
		<view class="login-header">
			<text class="welcome-text">欢迎登录</text>
			<text class="subtitle">请使用您的账号登录</text>
		</view>

		<view class="form-container">
			<view class="user-input">
				<view class="user-icon">
					<uni-icons type="phone" size="30" color="#7e57c2"></uni-icons>
				</view>
				<view>
					<input type="text" placeholder="请输入手机号" v-model="phone">
				</view>
			</view>
			<view class="user-input">
				<view class="user-icon">
					<uni-icons type="locked" size="30" color="#7e57c2"></uni-icons>
				</view>
				<view>
					<input type="password" placeholder="请输入密码" v-model="password">
				</view>
			</view>
			<view class="login-button" @tap="doLogin">登录</view>
		</view>
	</view>
</template>

<script>
	import UniIcons from "/components/uni-icons/uni-icons.vue"
	export default {
		data() {
			return {
				phone: "",
				password: ""
			}
		},
		methods: {
			doLogin() {
				this.phone = this.phone.trim();
				this.password = this.password.trim();
				if (this.phone == "" || this.password == "") {
					uni.showToast({
						title: "手机号和密码必须输入",
						icon: "error"
					});
					return;
				}
				if (!/^1[3589][0-9]{9}$/.test(this.phone)) {
					uni.showToast({
						title: "手机号错误",
						icon: "error"
					});
					return;
				}
				uni.request({
					url: "/api/user/login",
					data: {
						phone: this.phone,
						password: this.password
					},
					success: (resp) => {
						if (resp.data.code) {
							uni.showToast({
								title: resp.data.message,
								icon: "error"
							});
						} else {
							getApp().globalData.user = resp.data.data;
							uni.showToast({
								title: "登录成功！",
								icon: "success"
							});
							setTimeout(() => {
								uni.switchTab({
									url: "/pages/home/home"
								})
							}, 1500);
						}
					}
				})
			}
		},
	}
</script>

<style scoped lang="scss">
	.status_bar {
		height: var(--status-bar-height);
		width: 100%;
	}

	.login-wrap {
		height: 100vh;
		background: linear-gradient(135deg, #8e44ad, #9b59b6, #e84393);
		display: flex;
		flex-direction: column;
		padding: 0 50rpx;
	}

	.login-header {
		padding-top: 120rpx;
		margin-bottom: 100rpx;
		text-align: center;

		.welcome-text {
			display: block;
			font-size: 56rpx;
			font-weight: bold;
			color: #fff;
			margin-bottom: 20rpx;
		}

		.subtitle {
			font-size: 32rpx;
			color: rgba(255, 255, 255, 0.8);
		}
	}

	.form-container {
		background: rgba(255, 255, 255, 0.95);
		border-radius: 30rpx;
		padding: 60rpx 40rpx;
		box-shadow: 0 20rpx 40rpx rgba(0, 0, 0, 0.1);
		backdrop-filter: blur(10rpx);
	}

	.login-button {
		width: 100%;
		margin: 60rpx auto 0;
		text-align: center;
		background: linear-gradient(135deg, #8e44ad, #9b59b6);
		height: 90rpx;
		line-height: 90rpx;
		font-size: 36rpx;
		font-weight: bold;
		color: #fff;
		border-radius: 45rpx;
		box-shadow: 0 10rpx 20rpx rgba(142, 68, 173, 0.3);
		transition: all 0.3s;
	}

	.login-button:active {
		transform: scale(0.98);
		box-shadow: 0 5rpx 15rpx rgba(142, 68, 173, 0.4);
	}

	.user-input {
		width: 100%;
		margin: 40rpx auto;
		display: flex;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 45rpx;
		overflow: hidden;
		box-shadow: 0 8rpx 20rpx rgba(0, 0, 0, 0.08);
		background: rgba(255, 255, 255, 0.9);
		border: 2rpx solid #f0f0f0;

		view {
			background-color: transparent;
			width: 100%;

			input {
				position: relative;
				top: 16rpx;
				height: 60rpx;
				line-height: 60rpx;
				font-size: 32rpx;
				padding-left: 20rpx;
				color: #333;
			}

			input::placeholder {
				color: #aaa;
				font-size: 30rpx;
			}
		}

		view.user-icon {
			width: 100rpx;
			border-radius: 0;
			padding: 0rpx 0 20rpx 30rpx;
			display: flex;
			align-items: center;
			justify-content: center;
		}
	}

	.user-input:focus-within {
		box-shadow: 0 8rpx 25rpx rgba(142, 68, 173, 0.3);
		background: rgba(255, 255, 255, 0.95);
		border-color: #9b59b6;
	}
</style>
