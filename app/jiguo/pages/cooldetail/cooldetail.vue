<template>
	<view class="detail-page">
		<image v-if="item.image" class="cover" :src="buildImageUrl(item.image)" mode="aspectFill"></image>
		<view class="content" v-if="item.id">
			<view class="name">{{item.name}}</view>
			<view class="title">{{item.title}}</view>
			<view class="meta">
				<text class="price">￥{{item.price || '暂无'}}</text>
				<text>点赞 {{item.thumbCount || 0}}</text>
				<text>评论 {{item.commentCount || 0}}</text>
			</view>
			<view class="desc">
				这是一款精选酷玩产品，适合对数码科技、智能硬件和生活效率感兴趣的用户浏览与收藏。
			</view>
		</view>
		<view class="empty" v-else>商品信息加载中...</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				id: 0,
				imageUrl: "",
				item: {}
			}
		},
		methods: {
			buildImageUrl(imagePath) {
				if (!imagePath) return "";
				return this.imageUrl + "/" + imagePath;
			},
			loadDetail() {
				uni.request({
					url: "/api/coolitem/detail/" + this.id,
					success: (resp) => {
						if (resp.data.code === 0) {
							this.item = resp.data.data;
						} else {
							uni.showToast({ title: resp.data.message || "加载失败", icon: "none" });
						}
					},
					fail: () => {
						uni.showToast({ title: "网络错误", icon: "error" });
					}
				})
			}
		},
		onLoad(data) {
			this.id = Number(data.id || 0);
			this.imageUrl = getApp().globalData.imageUrl;
			if (this.id) {
				this.loadDetail();
			}
		}
	}
</script>

<style scoped>
	.detail-page {
		min-height: 100vh;
		background: #f7f7f7;
	}

	.cover {
		width: 100%;
		height: 520rpx;
		background: #eee;
	}

	.content {
		background: #fff;
		padding: 34rpx;
	}

	.name {
		font-size: 42rpx;
		font-weight: 700;
		color: #222;
	}

	.title {
		margin-top: 18rpx;
		font-size: 30rpx;
		line-height: 1.6;
		color: #555;
	}

	.meta {
		display: flex;
		gap: 28rpx;
		align-items: center;
		margin-top: 28rpx;
		color: #888;
		font-size: 26rpx;
	}

	.price {
		color: #e53935;
		font-size: 38rpx;
		font-weight: 700;
	}

	.desc {
		margin-top: 34rpx;
		line-height: 1.8;
		font-size: 28rpx;
		color: #444;
	}

	.empty {
		padding: 60rpx;
		text-align: center;
		color: #888;
	}
</style>
