<template>
	<view class="mine-wrap">
		<!-- 顶部用户信息区域 -->
		<view class="top-wrap">
			<!-- 未登录状态 -->
			<view v-if="user==null" class="not-logined">
				<view class="head">
					<uni-icons type="contact" size="100" color="gray"></uni-icons>
				</view>
				<view class="name" @tap="gotoLogin">点击登录</view>
			</view>

			<!-- 已登录状态 -->
			<view v-else class="logined">
				<view class="head">
					<image :src="buildImageUrl(user.image)" mode="aspectFill" @error="handleImageError"></image>
				</view>
				<view class="edit-image">
					<uni-icons type="compose" color="white" size="18"></uni-icons>
				</view>
				<view class="name">{{user.name}}</view>
				<view class="user-info">
					<text class="white">关注</text>
					<text class="count">20</text>
					<text class="white">|</text>
					<text class="white">粉丝</text>
					<text class="count">30</text>
				</view>
			</view>
		</view>

		<!-- 底部内容（仅登录后显示） -->
		<view v-if="user!=null" class="bottom-wrap">
			<view class="menu-card">
				<view class="user-item">
					<view class="item-left">
						<view class="icon-wrap" style="background-color: #f0f7ff;">
							<uni-icons type="list" color="#3cc51f" size="20"></uni-icons>
						</view>
						<text>我的订单</text>
					</view>
					<view class="item-right">
						<text class="order-count">1</text>
						<uni-icons type="right" color="#999" size="16"></uni-icons>
					</view>
				</view>

				<view class="user-item">
					<view class="item-left">
						<view class="icon-wrap" style="background-color: #fff0f0;">
							<uni-icons type="heart" color="#ff6b6b" size="20"></uni-icons>
						</view>
						<text>我的关注</text>
					</view>
					<view class="item-right">
						<text class="order-count">3</text>
						<uni-icons type="right" color="#999" size="16"></uni-icons>
					</view>
				</view>

				<view class="user-item">
					<view class="item-left">
						<view class="icon-wrap" style="background-color: #f8f0ff;">
							<uni-icons type="star" color="#9c27b0" size="20"></uni-icons>
						</view>
						<text>我的收藏</text>
					</view>
					<view class="item-right">
						<text class="order-count">4</text>
						<uni-icons type="right" color="#999" size="16"></uni-icons>
					</view>
				</view>

				<view class="user-item">
					<view class="item-left">
						<view class="icon-wrap" style="background-color: #fff9f0;">
							<uni-icons type="wallet" color="#ff9800" size="20"></uni-icons>
						</view>
						<text>我的发布</text>
					</view>
					<view class="item-right">
						<text class="order-count">4</text>
						<uni-icons type="right" color="#999" size="16"></uni-icons>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import UniIcons from "/components/uni-icons/uni-icons.vue"
	export default {
		data() {
			return {
				imageUrl: "",
				user: null,
				defaultAvatar: "tx.jpg"
			}
		},
		methods: {
		    gotoLogin() {
		        // 添加登录跳转逻辑
		        uni.navigateTo({
		            url: '/pages/login/login'
		        })
		    },
		    handleImageError() {
		        // 当头像加载失败时，设置为默认头像
		        this.user.image = this.defaultAvatar;
		    },
			// 构建完整的图片URL，添加时间戳防止缓存
			buildImageUrl(imagePath) {
				// 如果没有设置头像，使用默认头像
				if (!imagePath) {
					imagePath = this.defaultAvatar;
				}
				// 拼接完整的图片URL，并添加时间戳防止缓存
				const timestamp = new Date().getTime();
				return this.imageUrl + "/" + imagePath + "?t=" + timestamp;
			}
		},
		onLoad() {
			this.imageUrl = getApp().globalData.imageUrl;
			this.user = getApp().globalData.user;
		},
		onShow() {
			// 每次页面显示时都检查用户登录状态
			this.user = getApp().globalData.user;
		},
		components: {
			UniIcons
		}
	}
</script>

<style scoped lang="scss">
	.mine-wrap {
		background-color: #f5f5f5;
		min-height: 100vh;

		.top-wrap {
			height: 500rpx;
			background: linear-gradient(135deg, #3cc51f, #2a9d8f);
			position: relative;
			border-bottom-left-radius: 30rpx;
			border-bottom-right-radius: 30rpx;
			box-shadow: 0 10rpx 30rpx rgba(60, 197, 31, 0.2);

			.logined {
				.head {
					width: 160rpx;
					height: 160rpx;
					position: relative;
					margin: 0 auto;
					top: 80rpx;
					border: 6rpx solid rgba(255, 255, 255, 0.3);
					border-radius: 50%;
					overflow: hidden;
					box-shadow: 0 10rpx 20rpx rgba(0, 0, 0, 0.1);

					image {
						width: 100%;
						height: 100%;
						border-radius: 50%;
					}
				}

				.edit-image {
					position: absolute;
					top: 210rpx;
					left: 50%;
					margin-left: 30rpx;
					width: 50rpx;
					height: 50rpx;
					border-radius: 50%;
					display: flex;
					align-items: center;
					justify-content: center;
					box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.2);
					background-color: rgba(0, 0, 0, 0.4);
					backdrop-filter: blur(10rpx);
				}

				.name {
					width: 100%;
					position: relative;
					margin: 0 auto;
					top: 100rpx;
					text-align: center;
					font-size: 42rpx;
					font-weight: bold;
					color: #fff;
					text-shadow: 0 2rpx 4rpx rgba(0, 0, 0, 0.2);
				}

				.user-info {
					width: 60%;
					margin: 120rpx auto 0;
					text-align: center;
					display: flex;
					justify-content: center;
					align-items: center;

					text {
						margin: 0 15rpx;
					}

					.white {
						color: rgba(255, 255, 255, 0.9);
						font-size: 28rpx;
					}

					.count {
						color: #fff;
						font-weight: bold;
						font-size: 32rpx;
					}
				}
			}

			.not-logined {
				.head {
					width: 160rpx;
					position: relative;
					margin: 0 auto;
					top: 100rpx;
					text-align: center;
					opacity: 0.8;
				}

				.name {
					width: 200rpx;
					position: relative;
					margin: 0 auto;
					top: 120rpx;
					text-align: center;
					font-size: 36rpx;
					color: rgba(255, 255, 255, 0.9);
					padding: 16rpx 32rpx;
					background: rgba(255, 255, 255, 0.2);
					border-radius: 50rpx;
					backdrop-filter: blur(10rpx);
					border: 1rpx solid rgba(255, 255, 255, 0.3);
					transition: all 0.3s;
				}

				.name:active {
					background: rgba(255, 255, 255, 0.3);
					transform: scale(0.98);
				}
			}
		}

		.bottom-wrap {
			padding: 30rpx;
			margin-top: -30rpx;

			.menu-card {
				background: #fff;
				border-radius: 20rpx;
				box-shadow: 0 10rpx 30rpx rgba(0, 0, 0, 0.06);
				overflow: hidden;
			}

			.user-item {
				display: flex;
				align-items: center;
				justify-content: space-between;
				padding: 30rpx;
				border-bottom: 1rpx solid #f0f0f0;

				&:last-child {
					border-bottom: none;
				}

				.item-left {
					display: flex;
					align-items: center;
					flex: 1;

					.icon-wrap {
						width: 80rpx;
						height: 80rpx;
						border-radius: 20rpx;
						display: flex;
						align-items: center;
						justify-content: center;
						margin-right: 24rpx;
					}

					text {
						font-size: 32rpx;
						color: #333;
						font-weight: 500;
					}
				}

				.item-right {
					display: flex;
					align-items: center;

					.order-count {
						color: #999;
						font-size: 28rpx;
						margin-right: 20rpx;
					}
				}
			}
		}
	}
</style>