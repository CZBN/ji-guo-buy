<template>
	<view class="detail-page">
		<image v-if="item.image" class="cover" :src="buildImageUrl(item.image)" mode="aspectFill"></image>
		<view class="content" v-if="item.id">
			<view class="name">{{item.name}}</view>
			<view class="title">{{item.title}}</view>
			<view class="price">参考价 ￥{{item.price || '暂无'}}</view>
			<view class="desc">
				导购内容从外观、功能、适用场景和购买建议四个角度进行展示，帮助用户快速判断产品是否值得关注。
			</view>
		</view>
		<view class="empty" v-else>导购信息加载中...</view>
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
					url: "/api/guideitem/detail/" + this.id,
					success: (resp) => {
						if (resp.data.code === 0) {
							this.item = resp.data.data;
						} else {
							uni.showToast({ title: resp.data.message || "加载失败", icon: "none" });
						}
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
		font-size: 40rpx;
		font-weight: 700;
		color: #222;
	}

	.title,
	.desc {
		margin-top: 22rpx;
		font-size: 29rpx;
		line-height: 1.7;
		color: #555;
	}

	.price {
		margin-top: 28rpx;
		color: #e53935;
		font-size: 34rpx;
		font-weight: 700;
	}

	.empty {
		padding: 60rpx;
		text-align: center;
		color: #888;
	}
</style>
